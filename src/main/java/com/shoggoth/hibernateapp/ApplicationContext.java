package com.shoggoth.hibernateapp;

import com.shoggoth.hibernateapp.controller.Controller;
import com.shoggoth.hibernateapp.controller.command.CommandContainer;
import com.shoggoth.hibernateapp.controller.command.CommandType;
import com.shoggoth.hibernateapp.controller.command.impl.DefaultCommand;
import com.shoggoth.hibernateapp.controller.command.impl.ExitCommand;
import com.shoggoth.hibernateapp.controller.command.impl.HelpCommand;
import com.shoggoth.hibernateapp.controller.command.impl.skill.*;
import com.shoggoth.hibernateapp.controller.command.impl.specialty.*;
import com.shoggoth.hibernateapp.model.entity.DeveloperEntity;
import com.shoggoth.hibernateapp.model.entity.SkillEntity;
import com.shoggoth.hibernateapp.model.entity.SpecialtyEntity;
import com.shoggoth.hibernateapp.model.repository.impl.DeveloperRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SkillRepositoryImpl;
import com.shoggoth.hibernateapp.model.repository.impl.SpecialtyRepositoryImpl;
import com.shoggoth.hibernateapp.servise.SkillService;
import com.shoggoth.hibernateapp.servise.SpecialtyService;
import com.shoggoth.hibernateapp.servise.TransactionInterceptor;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSkillMapper;
import com.shoggoth.hibernateapp.servise.mapper.DtoToSpecialtyMapper;
import com.shoggoth.hibernateapp.servise.mapper.SkillToDtoMapper;
import com.shoggoth.hibernateapp.servise.mapper.SpecialtyToDtoMapper;
import com.shoggoth.hibernateapp.view.UserInterface;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ApplicationContext {
    public Controller init() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var configuration = new Configuration();
        configuration.addAnnotatedClass(SpecialtyEntity.class);
        configuration.addAnnotatedClass(SkillEntity.class);
        configuration.addAnnotatedClass(DeveloperEntity.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        var sessionFactory = configuration.buildSessionFactory();

        var currentSession = (Session) Proxy.newProxyInstance(AppRunner.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, methodArgs) -> method.invoke(sessionFactory.getCurrentSession(), methodArgs));

        var specialtyRepository = new SpecialtyRepositoryImpl(SpecialtyEntity.class, currentSession);
        var dtoToSpecialtyMapper = new DtoToSpecialtyMapper();
        var specialtyToDtoMapper = new SpecialtyToDtoMapper();

        var skillRepository = new SkillRepositoryImpl(SkillEntity.class, currentSession);
        var dtoToSkillMapper = new DtoToSkillMapper();
        var skillToDtoMapper = new SkillToDtoMapper();

        var transactionInterceptor = new TransactionInterceptor(sessionFactory);



        var specialtyService = new ByteBuddy()
                .subclass(SpecialtyService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(AppRunner.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(SpecialtyRepositoryImpl.class, DtoToSpecialtyMapper.class, SpecialtyToDtoMapper.class)
                .newInstance(specialtyRepository, dtoToSpecialtyMapper, specialtyToDtoMapper);



        var skillService = new ByteBuddy()
                .subclass(SkillService.class)
                .method(ElementMatchers.any())
                .intercept(MethodDelegation.to(transactionInterceptor))
                .make()
                .load(AppRunner.class.getClassLoader())
                .getLoaded()
                .getDeclaredConstructor(SkillRepositoryImpl.class, DtoToSkillMapper.class, SkillToDtoMapper.class)
                .newInstance(skillRepository, dtoToSkillMapper, skillToDtoMapper);


        var developerRepository = new DeveloperRepositoryImpl(DeveloperEntity.class, currentSession);

        var userInterface = new UserInterface(System.in);
        var defaultCommand = new DefaultCommand(userInterface);
        var commandContainer = new CommandContainer(defaultCommand);
        commandContainer.addCommand(CommandType.EXIT.getName(), new ExitCommand(userInterface));
        commandContainer.addCommand(CommandType.HELP.getName(), new HelpCommand(userInterface));
        commandContainer.addCommand(CommandType.ADD_SPECIALTY.getName(), new AddSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.GET_SPECIALTY.getName(), new GetSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.GET_ALL_SPECIALTYS.getName(), new GetAllSpecialtysCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.DELETE_SPECIALTY.getName(), new DeleteSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.UPDATE_SPECIALTY.getName(), new UpdateSpecialtyCommand(userInterface, specialtyService));
        commandContainer.addCommand(CommandType.ADD_SKILL.getName(), new AddSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.GET_SKILL.getName(), new GetSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.GET_ALL_SKILLS.getName(), new GetAllSkillsCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.DELETE_SKILL.getName(), new DeleteSkillCommand(userInterface, skillService));
        commandContainer.addCommand(CommandType.UPDATE_SKILL.getName(), new UpdateSkillCommand(userInterface, skillService));
        return new Controller(userInterface, commandContainer);

    }
}

package com.github.nikandpro.requestWeb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nikandpro.configuration.DatabaseConfiguration;
import com.github.nikandpro.modelDB.Food;
import com.github.nikandpro.modelDB.User;
import com.github.nikandpro.modelDB.statuses.UserStatus;
import com.github.nikandpro.tools.ObjectMapperFactory;
import com.github.nikandpro.tools.SecurityService;
import com.github.nikandpro.tools.Service;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;

public class FoodController {
    public static void createFood(Context ctx) throws IOException, SQLException {
        if (SecurityService.authentication(ctx)) {
            UserStatus userStatus = Service.findUser(ctx).getUserStatus();
            if (userStatus == UserStatus.admin || userStatus == UserStatus.seller) {
                String json = ctx.body();
                Food food;
                ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Food.class);
                food = obMap.readValue(json, Food.class);
                food.setUser(Service.findUser(ctx));
                System.out.println(food.toString());
                DatabaseConfiguration.foodDao.create(food);
                //recording Tag_Food in BD
                Service.handleFoodTag(ctx.queryParam("subtag"), food);
                ctx.status(201);
            } else {
                ctx.status(403);
            }
        } else {
            ctx.status(401);
        }
    }

    public static void getAllFood(Context ctx) throws SQLException, JsonProcessingException {
        System.out.println("GetAllFood");
        String listSubtag= ctx.queryParam("subtag");
        if (listSubtag==null ||  listSubtag.equals("")) {
            ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Food.class);
            System.out.println("obMap getAllFood");
            ctx.result(obMap.writeValueAsString(DatabaseConfiguration.foodDao.queryForAll()));
            ctx.status(200);
        } else {
            Service.findFoodSubtag(ctx);
        }
    }

    public static void getFood(Context ctx) throws SQLException, JsonProcessingException {
        System.out.println("");
        if (SecurityService.authentication(ctx)) {
            ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Food.class);
            int idFood = Integer.parseInt(ctx.pathParam("id"));
            ctx.result(obMap.writeValueAsString(DatabaseConfiguration.foodDao.queryForId(idFood)));
            ctx.status(200);
        } else {
            ctx.status(401);
        }
    }

    public static void updateFood(Context ctx) throws IOException, SQLException {
        if (SecurityService.authentication(ctx)) {
            UserStatus userStatus = Service.findUser(ctx).getUserStatus();
            if (userStatus == UserStatus.admin || userStatus == UserStatus.seller) {
                int idFood = Integer.parseInt(ctx.pathParam("id"));
                String json = ctx.body();
                Food food;
                ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Food.class);
                food = obMap.readValue(json, Food.class);
                food.setId(idFood);
                User user = Service.findUser(ctx);
                if (DatabaseConfiguration.foodDao.queryForId(idFood).getUser().getId() == user.getId()) {
                    food.setUser(user);
                    DatabaseConfiguration.foodDao.update(food);
                    ctx.status(201);
                } else {
                    ctx.status(403);
                }
            } else {
                ctx.status(403);
            }
        } else {
            ctx.status(401);
        }
    }

    public static void deleteFood(Context ctx) throws SQLException {
        UserStatus userStatus;
        if (SecurityService.authentication(ctx)) {
            userStatus = Service.findUser(ctx).getUserStatus();
            if (userStatus == UserStatus.admin) {
                int id = Integer.parseInt(ctx.pathParam("id"));
                DatabaseConfiguration.foodDao.deleteById(id);
                ctx.status(204);
            } else if (userStatus == UserStatus.seller){
                int id = Integer.parseInt(ctx.pathParam("id"));
                if (DatabaseConfiguration.foodDao.queryForId(id).getUser().getId()== Service.findUser(ctx).getId()) {
                    DatabaseConfiguration.foodDao.deleteById(id);
                }
                ctx.status(204);
            } else {
                ctx.status(403);
            }
        } else {
            ctx.status(401);
        }
    }
}

package com.github.nikandpro.requestWeb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nikandpro.configuration.DatabaseConfiguration;
import com.github.nikandpro.modelDB.Tag;
import com.github.nikandpro.modelDB.statuses.UserStatus;
import com.github.nikandpro.tools.ObjectMapperFactory;
import com.github.nikandpro.tools.SecurityService;
import com.github.nikandpro.tools.Service;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;

public class TagController {

    public static void createTag(Context ctx) throws SQLException, IOException {
        if (SecurityService.authentication(ctx)) {
            UserStatus userStatus = Service.findUser(ctx).getUserStatus();
            System.out.println("show createTag");
            if (userStatus == UserStatus.admin) {
                String json = ctx.body();
                Tag tag;
                // справь потом везде так, где не нужен ObjectMapperFactory
                ObjectMapper obMap = new ObjectMapper();
                tag = obMap.readValue(json, Tag.class);
                DatabaseConfiguration.tagDao.create(tag);
                ctx.status(201);
            } else {
                ctx.status(403);
            }
        } else {
            ctx.status(401);
        }
    }

    public static void getTagName(Context ctx) throws SQLException, JsonProcessingException {
        System.out.println("Get_Tag_Name");
        ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Tag.class);
        ctx.result(obMap.writeValueAsString(DatabaseConfiguration.tagDao.queryForAll()));
        ctx.status(200);
    }

    public static void getTagID(Context ctx) throws SQLException, JsonProcessingException {
        System.out.println("Get_Tag_ID");
        int id = Integer.parseInt(ctx.pathParam("id"));
        ObjectMapper obMap = ObjectMapperFactory.createObjectMapper(Tag.class);
        System.out.println(obMap.writeValueAsString(DatabaseConfiguration.tagDao.queryForAll()));
        ctx.result(obMap.writeValueAsString(DatabaseConfiguration.tagDao.queryForId(id)));
        ctx.status(200);
    }

    public static void deleteTag(Context ctx) throws SQLException, IOException {
        if (SecurityService.authentication(ctx)) {
            UserStatus userStatus = Service.findUser(ctx).getUserStatus();
            if (userStatus == UserStatus.admin) {
                int idTag = Integer.parseInt(ctx.pathParam("id"));
                String json = ctx.body();
                DatabaseConfiguration.tagDao.deleteById(idTag);
                ctx.status(204);
            } else {
                ctx.status(403);
            }
        } else {
            ctx.status(401);
        }
    }
}

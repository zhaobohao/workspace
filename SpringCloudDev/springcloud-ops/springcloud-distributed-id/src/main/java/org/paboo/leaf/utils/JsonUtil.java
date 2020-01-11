/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.paboo.leaf.utils;

import com.google.gson.*;

import java.util.List;

/**
 * @author Leonard Woo
 */
public class JsonUtil {

    private static Gson gson;

    static {
        gson = new Gson();
    }

    public static <T> String toJson(T t){
        return gson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> objectType) {
        return gson.fromJson(json, objectType);
    }

    public static String fromJsonToString(String json, String key) {
        return fromJsonObject(json).get(key).getAsString();
    }

    public static JsonObject fromJsonObject(String json) {
        return new JsonParser().parse(json).getAsJsonObject();
    }

    public static <T> JsonObject toJsonObject(T t) {
        return gson.toJsonTree(t).getAsJsonObject();
    }

    public static JsonElement toJsonElement(List list) {
        return gson.toJsonTree(list);
    }

}

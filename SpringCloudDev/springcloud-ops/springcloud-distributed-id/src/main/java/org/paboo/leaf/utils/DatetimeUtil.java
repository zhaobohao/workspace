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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * @author Leonard Woo
 */
public class DatetimeUtil {

    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String now(){
        return format(LocalDateTime.now());
    }

    public static String format(TemporalAccessor temporal){
        return format(ISO8601, temporal);
    }

    public static String format(String pattern, TemporalAccessor temporal) {
        return DateTimeFormatter.ofPattern(pattern).format(temporal);
    }

    /**
     *
     * @return UTC time
     */
    public static String generateOrderDatetime(){
        return format("yyyyMMddHHmmss", LocalDateTime.now());
    }

    public static long getEpoch() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+0"));
    }

    public static TemporalAccessor parse(String timestamp){
        return parse(ISO8601, timestamp);
    }

    public static TemporalAccessor parse(String pattern, String timestamp){
        return DateTimeFormatter.ofPattern(pattern).parse(timestamp);
    }

    public static TemporalAccessor parse(String pattern, Locale locale, String timestamp){
        return DateTimeFormatter.ofPattern(pattern, locale).parse(timestamp);
    }

    public static TemporalAccessor parse(long epoch, ZoneId zoneId) {
        return Instant.ofEpochMilli(epoch).atZone(zoneId);
    }

    public static TemporalAccessor parse(long epoch) {
        return parse(epoch, Clock.systemUTC().getZone());
    }

}

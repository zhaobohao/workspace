package com.kyro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Msg {
    public String name;
    public Integer id;
    public String nickName;
    public Date cd;

    public Map<String ,Integer> nameMap;

    public Person person;

}

package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.converter.BirthDateConverter;
import org.example.model.User;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public Configuration getConfiguration(){
        return new Configuration()
                .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
                .addAttributeConverter(BirthDateConverter.class)
                .addAnnotatedClass(User.class);
    }

}

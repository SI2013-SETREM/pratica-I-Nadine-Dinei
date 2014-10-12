
package reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dinei A. Rockenbach
 */
public class ReflectionUtil {
    
    public static Object getStaticAttibute(Class<?> cls, String attr) {
        try {
            Field attribute = cls.getField(attr);
            attribute.setAccessible(true);
            return attribute.get(null);
        } catch (NoSuchFieldException ex) {
            //Nestes casos, retorna null
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Class<?> getAttributeType(Class<?> cls, String attr) {
        try {
            Field attribute = cls.getField(attr);
            attribute.setAccessible(true);
            return attribute.getType();
        } catch (NoSuchFieldException ex) {
            return getMethodGetReturnType(cls, attr); //Busca se o atributo possui uma função GET
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Class<?> getMethodReturnType(Class<?> cls, String method) {
        try {
            Method func = cls.getMethod(method);
            func.setAccessible(true);
            return func.getReturnType();
        } catch (NoSuchMethodException ex) {
            //Nestes casos, retorna null
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Class<?> getMethodGetReturnType(Class<?> cls, String attribute) {
        return getMethodReturnType(cls, "get" + Character.toUpperCase(attribute.charAt(0)) + attribute.substring(1));
    }
    
    public static Object getStaticMethod(Class<?> cls, String method) {
        try {
            Method func = cls.getMethod(method);
            func.setAccessible(true);
            return func.invoke(null);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getDBTableName(Class<?> cls) {
        String dbtable = (String) getStaticAttibute(cls, "dbTable");
        if (dbtable == null) 
            return cls.getSimpleName().toLowerCase();
        else 
            return dbtable;
    }
    
}

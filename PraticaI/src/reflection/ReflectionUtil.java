
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
    
    // Trabalhando com atributos
    public static Object getAttibute(Class<?> cls, String attr) {
        try {
            Field attribute = cls.getField(attr);
            attribute.setAccessible(true);
            return attribute.get(null);
        } catch (NoSuchFieldException ex) {
            //Nestes casos, retorna null
        } catch (Exception ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Object getAttibute(Object obj, String attr) {
        return getAttibute(obj, attr, true);
    }
    public static Object getAttibute(Object obj, String attr, boolean checkGetMethod) {
        try {
            Field attribute = obj.getClass().getField(attr);
            attribute.setAccessible(true);
            return attribute.get(obj);
        } catch (NoSuchFieldException ex) {
            if (checkGetMethod)
                return getMethod(obj, "get" + Character.toUpperCase(attr.charAt(0)) + attr.substring(1));
        } catch (Exception ex) {
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
            return getMethodReturnType(cls, "get" + Character.toUpperCase(attr.charAt(0)) + attr.substring(1));
        } catch (Exception ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean isAttributeExists(Class<?> cls, String attr) {
        try {
            cls.getField(attr);
            return true;
        } catch (NoSuchFieldException ex) {
            return false;
        }
    }
    public static boolean isAttributeExists(Object obj, String attr) {
        try {
            obj.getClass().getField(attr);
            return true;
        } catch (NoSuchFieldException ex) {
            return false;
        }
    }
    public static boolean isAttributeExists(Object obj, String attr, boolean checkGetMethod) {
        boolean r = isAttributeExists(obj, attr);
        if (!r && checkGetMethod) {
            r = isMethodExists(obj, "get" + Character.toUpperCase(attr.charAt(0)) + attr.substring(1));
        }
        return r;
    }
    
    // Trabalhando com métodos
    public static Object getMethod(Class<?> cls, String method) {
        try {
            Method func = cls.getMethod(method);
            func.setAccessible(true);
            return func.invoke(null);
        } catch (NoSuchMethodException ex) {
            //Nestes casos, retorna null
        } catch (Exception ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static Object getMethod(Object obj, String method) {
        try {
            Method func = obj.getClass().getMethod(method);
            func.setAccessible(true);
            return func.invoke(obj);
        } catch (NoSuchMethodException ex) {
            //Nestes casos, retorna null
        } catch (Exception ex) {
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
    
    public static boolean isMethodExists(Object obj, String method) {
        try {
            obj.getClass().getMethod(method);
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }
    
    // Outras funções
    public static String getDBTableName(Class<?> cls) {
        String dbtable = (String) getAttibute(cls, "dbTable");
        if (dbtable == null) 
            return cls.getSimpleName().toLowerCase();
        else 
            return dbtable;
    }
    
}


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
    
    public static Class<?> getAttributeType(Object obj, String attr) {
        return getAttributeType(obj.getClass(), attr);
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
        return getMethod(cls, method, new Object[]{});
    }
    public static Object getMethod(Class<?> cls, String method, Object[] args) {
        //@TODO navegar nos args descobrindo o tipo
        try {
            Method func = null;
            switch (args.length) {
                case 2:
                case 1:
                    Class<?> clsType = getAttributeType(cls, method.replaceAll("^set", ""));
                    func = cls.getMethod(method, clsType);
                    break;
                case 0:
                default:
                    func = cls.getMethod(method);
                    break;
            }
            if (func != null) {
                func.setAccessible(true);
                Object r;
                switch (args.length) {
                    case 2:
                        r = func.invoke(null, args[0], args[1]);
                        break;
                    case 1:
                        r = func.invoke(null, args[0]);
                        break;
                    case 0:
                    default:
                        r = func.invoke(null);
                        break;
                }
                return r;
            }
        } catch (NoSuchMethodException ex) {
            //Nestes casos, retorna null
        } catch (Exception ex) {
            Logger.getLogger(ReflectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Object getMethod(Object obj, String method) {
        return getMethod(obj, method, new Object[]{});
    }
    public static Object getMethod(Object obj, String method, Object[] args) {
        try {
            Method func = null;
            switch (args.length) {
                case 2:
                case 1:
                    Class<?> clsType = getAttributeType(obj, method.replaceAll("^set", ""));
                    func = obj.getClass().getMethod(method, clsType);
                    break;
                case 0:
                default:
                    func = obj.getClass().getMethod(method);
                    break;
            }
            if (func != null) {
                func.setAccessible(true);
                Object r;
                switch (args.length) {
                    case 2:
                        r = func.invoke(obj, args[0], args[1]);
                        break;
                    case 1:
                        r = func.invoke(obj, args[0]);
                        break;
                    case 0:
                    default:
                        r = func.invoke(obj);
                        break;
                }
                return r;
            }
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
    
    //@TOOD preciso de uma checagem de método com parâmetros
    public static boolean isMethodExists(Object obj, String method) {
        return isMethodExists(obj.getClass(), method);
    }
    public static boolean isMethodExists(Class<?> cls, String method) {
        try {
            cls.getMethod(method);
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
    public static String getDBTableName(Object obj) {
        String dbtable = (String) getAttibute(obj, "dbTable");
        if (dbtable == null) 
            return obj.getClass().getSimpleName().toLowerCase();
        else 
            return dbtable;
    }
    
}

package model.log;

import interfaces.daos.LogDao;
import java.util.Date;
import model.pojos.Store;
import model.pojos.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Class for logging using AspectJ
 *
 * @author jonny
 */
@Aspect
public class LogAspect {

    LogDao logDao;

    public void setLogDao(LogDao logDao) {
        this.logDao = logDao;
    }

    /**
     * Logs services operations in the master controller
     *
     * @param jp
     * @param returnValue
     * @throws Throwable
     */
    @AfterReturning(pointcut = "execution(public * controllers.*Controller.*(..))",
    returning = "returnValue")
    public void afterReturning(JoinPoint jp, Object returnValue) throws Throwable {

        Object[] args = jp.getArgs();

        User executor = null;
        Store store = null;
        if (args.length > 0 && args[0] != null) {
            if (args[0] instanceof User) {
                executor = (User) args[0];
            } else if (args[0] instanceof String) {
                executor = (User) returnValue;
            }
            store = executor.getStore();
        }

        String executorString = getExecutorString(executor);

        String activityString = getActivityString(jp);

        activityString += "): " + returnValue;

        Date instantTime = getInstant();

        logDao.log(store, instantTime, executorString, activityString, true);
    }

    /**
     * Logs exceptions in the master controller
     *
     * @param jp
     * @param exception
     * @throws Throwable
     */
    @AfterThrowing(pointcut = "execution(public * controllers.*Controller.*(..))",
    throwing = "exception")
    public void afterThrowing(JoinPoint jp, Throwable exception) throws Throwable {

        Object[] args = jp.getArgs();

        User executor = null;
        Store store = null;
        if (args.length > 0) {
            if (args[0] instanceof User) {
                executor = (User) args[0];
                store = executor.getStore();
            }
        }

        String executorString = getExecutorString(executor);

        String activityString = getActivityString(jp);

        activityString += "): " + exception.getMessage();

        Date instantTime = getInstant();

        logDao.log(store, instantTime, executorString, activityString, false);
    }

    /**
     *
     * @return
     */
    private Date getInstant() {
        return new Date();
    }

    private String getExecutorString(User executor) {
        return executor != null
                ? executor.getLogin() + " (" + executor.getNamesurname() + ")"
                : "";
    }

    private String getActivityString(JoinPoint jp) {
        Object[] args = jp.getArgs();

        String[] parameterNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        String activityString = jp.getSignature().getName() + "(";
        if (args.length > 0) {

            if (args[0] instanceof User) {

                for (int i = 1; i < args.length; i++) {

                    activityString += parameterNames[i] + "=" + args[i];

                    if (i < args.length - 1) {
                        activityString += ", ";
                    }
                }
            } else if (args[0] instanceof String) {

                activityString += parameterNames[0] + "=" + args[0] + ", "
                        + parameterNames[1] + "=***";
            }
        }

        return activityString;
    }
}
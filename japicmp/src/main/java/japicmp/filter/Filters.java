package japicmp.filter;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtField;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Filters {
    private static final Logger LOGGER = Logger.getLogger(Filters.class.getName());
    private final List<Filter> includes = new ArrayList<>();
    private final List<Filter> excludes = new ArrayList<>();

    public List<Filter> getIncludes() {
        return includes;
    }

    public List<Filter> getExcludes() {
        return excludes;
    }

    public boolean includeClass(CtClass ctClass) {
        String name = ctClass.getName();
        for (Filter filter : excludes) {
            if (filter instanceof ClassFilter || filter instanceof PackageFilter) {
                if (filter.matches(ctClass)) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, "Excluding class '" + name + "' because class filter '" + filter + "' matches.");
                    }
                    return false;
                }
            }
        }
        int includeCount = 0;
        for (Filter filter : includes) {
            includeCount++;
            if (filter.matches(ctClass)) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Including class '" + name + "' because class filter '" + filter + "' matches.");
                }
                return true;
            }
        }
        if (includeCount > 0) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Excluding class '" + name + "' because no include matched.");
            }
            return false;
        }
        return true;
    }

    public boolean includeBehavior(CtBehavior ctMethod) {
        for (Filter filter : excludes) {
            if (filter instanceof BehaviorFilter) {
                if (filter.matches(ctMethod)) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, "Excluding method '" + ctMethod.getLongName() + "' because exclude method filter did match.");
                    }
                    return false;
                }
            }
        }
        int includesCount = 0;
        for (Filter filter : includes) {
            includesCount++;
            if (filter.matches(ctMethod)) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Including method '" + ctMethod.getLongName() + "' because include method filter matched.");
                }
                return true;
            }
        }
        if (includesCount > 0) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Excluding method '" + ctMethod.getLongName() + "' because no include matched.");
            }
            return false;
        }
        return true;
    }

    public boolean includeField(CtField ctField) {
        for (Filter filter : excludes) {
            if (filter instanceof FieldFilter) {
                if (filter.matches(ctField)) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE, "Excluding field '" + ctField.getName() + "' because exclude field filter did match.");
                    }
                    return false;
                }
            }
        }
        int includesCount = 0;
        for (Filter filter : includes) {
            includesCount++;
            if (filter.matches(ctField)) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE, "Including field '" + ctField.getName() + "' because include field filter matched.");
                }
                return true;
            }
        }
        if (includesCount > 0) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "Excluding field '" + ctField.getName() + "' because no include matched.");
            }
            return false;
        }
        return true;
    }
}

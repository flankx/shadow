package com.github.shadow.util;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import springfox.documentation.RequestHandler;

public class SwaggerUtil {

    public static Predicate<RequestHandler> basePackages(final List<String> basePackages) {
        return (input) -> (Boolean)declaringClass(input).map(handlerPackage(basePackages)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final List<String> basePackages) {
        return (input) -> {
            Iterator<String> var2 = basePackages.iterator();

            boolean isMatch;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                String strPackage = var2.next();
                isMatch = input.getPackage().getName().startsWith(strPackage);
            } while (!isMatch);

            return true;
        };
    }

    private static Optional<Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}

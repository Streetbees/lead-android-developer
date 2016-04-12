package lt.setkus.interviewtest.configuration.dagger;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}

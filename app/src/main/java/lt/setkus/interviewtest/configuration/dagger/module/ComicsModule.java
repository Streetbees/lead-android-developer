package lt.setkus.interviewtest.configuration.dagger.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import lt.setkus.interviewtest.configuration.dagger.PerActivity;
import lt.setkus.interviewtest.domain.interaction.GetComicsUseCase;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@Module
public class ComicsModule {

    @Provides
    @PerActivity
    @Named("comics")
    public GetComicsUseCase provideGetComicsUseCase(GetComicsUseCase getComicsUseCase) {
        return getComicsUseCase;
    }
}

package lt.setkus.interviewtest.configuration.dagger.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lt.setkus.interviewtest.UIThread;
import lt.setkus.interviewtest.data.executor.JobExecutor;
import lt.setkus.interviewtest.data.repository.MarvelDataRepository;
import lt.setkus.interviewtest.data.rest.MarvelApiConfiguration;
import lt.setkus.interviewtest.data.rest.RestClient;
import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import lt.setkus.interviewtest.domain.executor.ThreadExecutor;
import lt.setkus.interviewtest.domain.repository.MarvelRepository;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public RestClient providesRestClient() {
        return RestClient.newInstance(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public MarvelRepository providesMarvelRepository(RestClient restClient) {
        return new MarvelDataRepository(restClient);
    }

    @Singleton
    @Provides
    public ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Singleton
    @Provides
    public PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}

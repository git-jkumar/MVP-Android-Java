package olabs.kit.rxmvvm.registration.API;


import olabs.kit.network.rx.APIManager;
import olabs.kit.rxmvvm.registration.RegisterViewModel;
import olabs.kit.model.RegisterResponse;
import olabs.kit.model.RegistrationRequest;
import olabs.network.rxnetwork.APISubscriber;
import olabs.network.rxnetwork.RXEventBus;
import olabs.network.rxnetwork.RetroError;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public class RegistrationAPIListener extends APIManager {
    protected RegisterViewModel mRegisterViewModel;
    protected IRegistrationAPI mRegistrationAPI;
    public RegistrationAPIListener(RegisterViewModel registerViewModel) {
        super();
        mRegistrationAPI = (IRegistrationAPI) getServiceClient(IRegistrationAPI.class);
        this.mRegisterViewModel = registerViewModel;
    }


    public void doRegistration(RegistrationRequest registrationRequest){
        mRegistrationAPI.doRegistration(registrationRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(defaultSubscribeScheduler())
                .subscribe(new APISubscriber<RegisterResponse>());
    }

    @Override
    public void onError(RetroError errorMessage) {
        mRegisterViewModel.onError(errorMessage);
    }

    @Override
    public void onSuccess(Object o) {
        if (o instanceof RegisterResponse) {
            mRegisterViewModel.onRegistrationSuccess((RegisterResponse) o);
        }

    }

    @Override
    public void subscribe() {
        RXEventBus.getInstance().register(RetroError.class, this);
        RXEventBus.getInstance().register(RegisterResponse.class, this);
    }

}

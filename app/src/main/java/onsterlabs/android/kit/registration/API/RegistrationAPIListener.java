package onsterlabs.android.kit.registration.API;


import onsterlabs.android.kit.APIManager;
import onsterlabs.android.kit.BaseApplication;
import onsterlabs.android.kit.registration.RegisterViewModel;
import onsterlabs.android.kit.registration.model.RegisterResponse;
import onsterlabs.android.kit.registration.model.RegistrationRequest;
import onsterlabs.network.rxnetwork.APISubscriber;
import onsterlabs.network.rxnetwork.RXEventBus;
import onsterlabs.network.rxnetwork.RetroError;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public class RegistrationAPIListener extends APIManager{
    protected RegisterViewModel mRegisterViewModel;
    protected IRegistrationAPI mRegistrationAPI;
    public RegistrationAPIListener(BaseApplication application, RegisterViewModel registerViewModel) {
        super(application);
        mRegistrationAPI = application.getServiceClient(IRegistrationAPI.class);
        this.mRegisterViewModel = registerViewModel;
    }


    public void doRegistration(RegistrationRequest registrationRequest){
        mRegistrationAPI.doRegistration(registrationRequest)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
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
        subscriptions.add(RXEventBus.getInstance().register(RetroError.class, this));
        subscriptions.add(RXEventBus.getInstance().register(RegisterResponse.class, this));
    }

}

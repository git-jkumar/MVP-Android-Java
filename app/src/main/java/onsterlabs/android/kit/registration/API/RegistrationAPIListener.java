package onsterlabs.android.kit.registration.API;


import onsterlabs.android.kit.APIManager;
import onsterlabs.android.kit.registration.RegisterViewModel;
import onsterlabs.android.kit.registration.model.RegisterResponse;
import onsterlabs.android.kit.registration.model.RegistrationRequest;
import onsterlabs.network.RetroError;
import onsterlabs.network.rxnetwork.APISubscriber;
import onsterlabs.network.rxnetwork.RXEventBus;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public class RegistrationAPIListener extends APIManager{
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

package rynkbit.tk.coffeelist;

import android.view.View;

/**
 * Created by michael on 11/13/16.
 */
public class LoginClickListener implements View.OnClickListener {
    private MainController mController;

    public LoginClickListener(MainController mainController){
        mController = mainController;
    }
    @Override
    public void onClick(View view) {
        mController.askCredentials();
    }
}

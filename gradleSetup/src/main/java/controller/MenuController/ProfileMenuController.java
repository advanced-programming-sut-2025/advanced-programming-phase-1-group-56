package controller.MenuController;

import controller.CommandController;
import model.App;
import model.Enums.InfoRegexes;
import model.Result;
import model.User;

public class ProfileMenuController extends CommandController {
    public static Result manageChangePassword(String  newPassword,String oldPassword) {
        newPassword= newPassword.trim();
        oldPassword =oldPassword.trim();
        if(!App.getCurrentUser().getPassword().equals(oldPassword)) {
            return new Result(false,"password ghadimit eshatabahe dadash..");
        } else if(newPassword.equals(oldPassword)) {
            return new Result(false, "dadash password jadid hamun ghablie ke");
        } else if(!InfoRegexes.password.isValid(newPassword)) {
            return new Result(false, "password jadidet be dard khodet mikore x_x ");
        } else if(!(newPassword.length() <8)) {
            return new Result(false, "password istoo short");
        }
        App.getCurrentUser().setPassword(newPassword);
        return new Result(true,"az in be baad mituni ba password : '" + newPassword + "' login koni..(:");
    }

    public static Result manageChangeEmail(String newEmail) {
        newEmail= newEmail.trim();
        if(App.getCurrentUser().getEmail().equals(newEmail)) {
            return new Result(false, "dadash email jadid hamun ghablie ke");
        } else if(!InfoRegexes.email.isValid(newEmail)) {
            return new Result(false, "email jadidet be dard khodet mikore x_x ");
        }
        App.getCurrentUser().setEmail(newEmail);
        return new Result(true,"az in be baad mituni ba email : '" + newEmail + "' login koni..(:");
    }

    public static Result manageChangeUsername(String newUsername) {
        newUsername= newUsername.trim();
        if(App.getCurrentUser().getUsername().equals(newUsername)) {
            return new Result(false, "dadash username jadid hamun ghablie ke");
        } else if(!InfoRegexes.usersName.isValid(newUsername)) {
            return new Result(false, "username jadidet be dard khodet mikore x_x ");
        }
        App.getCurrentUser().setUsername(newUsername);
        return new Result(true,"shoma az in be baad jenab : '" + newUsername + "' hastid.");
    }

    public static Result manageChangeNickName(String newNickName) {
        newNickName= newNickName.trim();
        if(App.getCurrentUser().getName().equals(newNickName)) {
            return new Result(false, "dadash esm jadid hamun ghablie ke");
        } else if(!InfoRegexes.nickname.isValid(newNickName)) {
            return new Result(false, "in che esmie dige dadash?? x_x ");
        }
        App.getCurrentUser().setName(newNickName);
        return new Result(true,"chaker dash '" + newNickName + "'");
    }

    public static Result UserInfo() {
        User user = App.getCurrentUser();
        String info = "Dadash Shoma '" + user.getUsername() + "' " +
                "Molagahb be '" + user.getUsername() + "' Mibashid.\n" +
                user.getHighScore() + "$ balatarin emtiaz shome dar " +
                user.getNumOfGames() + "ta bazi mibashad..";

        return new Result(true, info);
    }
}

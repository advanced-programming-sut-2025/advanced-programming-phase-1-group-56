package io.src.controller.MenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.InfoRegexes;
import io.src.model.MakePasswordSHA_256;
import io.src.model.Result;
import io.src.model.User;

public class ProfileMenuController extends CommandController {
    public static Result manageChangePassword(String  newPassword,String oldPassword) {
        newPassword= newPassword.trim();
        oldPassword =oldPassword.trim();
        String hashedInputPassword = MakePasswordSHA_256.hashPassword(oldPassword, App.getCurrentUser().getSalt());//TODO
        if(!App.getCurrentUser().getPassword().equals(hashedInputPassword)){
            return new Result(false,"password ghadimit eshatabahe dadash.");
        } else if(newPassword.equals(oldPassword)) {
            return new Result(false, "dadash password jadid hamun ghablie ke");
        } else if(!InfoRegexes.password.isValid(newPassword)) {
            return new Result(false, "password jadidet be dard khodet mikore x_x ");
        } else if(!InfoRegexes.strongPassword.isValid(newPassword)) {
            return new Result(false, "please only use special character and letter and number for password!");
        } else if (newPassword.length() < 8 ) {
            return new Result(false, "password is too short!");
        }
        String salt = MakePasswordSHA_256.generateSalt();
        String hashPassword = MakePasswordSHA_256.hashPassword(newPassword, salt);
        App.getCurrentUser().setSalt(salt);
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
        }
        App.getCurrentUser().setName(newNickName);
        return new Result(true,"chaker dash '" + newNickName + "'");
    }

    public static Result UserInfo() {
        User user = App.getCurrentUser();
        String info = "Dadash Shoma '" + user.getUsername() + "' " +
                "Molagahb be '" + user.getName() + "' Mibashid.\n" +
                user.getHighScore() + "$ balatarin emtiaz shome dar " +
                user.getNumOfGames() + "ta bazi mibashad..";//TODO

        return new Result(true, info);
    }
}

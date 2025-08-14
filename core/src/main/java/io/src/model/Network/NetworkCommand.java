package io.src.model.Network;

import java.util.HashMap;

public enum NetworkCommand {
    updateEmote,
    emote,
    ready_for_state,
    request_game_state,
    OWNERtoDC,
    DCtoOWNER,
    updateObject,
    load,
    updatePlayer,
    Game,
    username,
    online_Users,
    list_lobbies,
    join_lobby,
    leave_lobby,
    create_lobby,
    kick_user,
    remove_lobby,
    lobby_chat_message,
    error,
    toggle_ready,
    start;
}

package com.softy.ori.multiplayer.conectivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.google.gson.Gson;
import com.softy.ori.customization.Config;
import com.softy.ori.multiplayer.model.DirectionChangedMessage;
import com.softy.ori.multiplayer.model.GameState;
import com.softy.ori.multiplayer.model.GameUpdateMessage;
import com.softy.ori.multiplayer.model.PlayerJoinMessage;
import com.softy.ori.util.Vector;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.Stomp.ConnectionProvider;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

@SuppressLint("CheckResult")
public class AndroidClient {

    private static AndroidClient instance;

    private static final String HTTP = "https://";
    private static final String WS = "ws://";

    private static final String IPV4 = "ori-application.herokuapp.com/";

    private static final String BASE_URL = HTTP + IPV4; // append ":8080"
    private static final String WEBSOCKET_URL = WS + IPV4 + "ori-websocket/websocket"; // append ":8080" in between IPV4.

    private static final String FIND_GAME_URL = BASE_URL + "game";
    private static final String JOIN_GAME_URL = BASE_URL + "game/"; // + {id} + /join

    private static final String SUBSCRIBE_GAME_DEST = "/ori/subscribe/"; // + {id}
    private static final String UPDATE_PLAYER_DEST = "/ori/direction/";
    private static final String QUIT_GAME_DEST = "/ori/quit/";

    private final RequestQueueSingleton requestQueue;
    private final StompClient client;
    private final Config config;
    private final Gson gson;

    private AndroidClient(Context context) {
        this.client = Stomp.over(ConnectionProvider.JWS, WEBSOCKET_URL);
        this.config = Config.getInstance(context);
        this.requestQueue = RequestQueueSingleton.getInstance(context);
        this.gson = new Gson();

        openConnection();
    }

    public static AndroidClient getInstance(Context context) {
        if (instance == null)
            instance = new AndroidClient(context);

        return instance;
    }

    /**
     * return gameId
     */
    public Single<String> findGame() {

        return new Single<String>() {
            @Override
            protected void subscribeActual(SingleObserver<? super String> observer) {
                final GetRequest<String> findGameRequest = new GetRequest<>(FIND_GAME_URL,
                        String.class, null,
                        observer::onSuccess,
                        observer::onError
                );

                requestQueue.addToRequestQueue(findGameRequest);
            }
        };
    }

    public Single<GameState> join(String gameId) {
        final String url = JOIN_GAME_URL + gameId + "/join";
        final PlayerJoinMessage body = config.constructMessage();

        return new Single<GameState>() {
            @Override
            protected void subscribeActual(SingleObserver<? super GameState> observer) {
                CustomRequest<PlayerJoinMessage ,GameState> postReq = new CustomRequest<>(Method.POST, url,
                        PlayerJoinMessage.class, GameState.class, body,
                        observer::onSuccess,
                        error -> Log.d("CLIENT", "Failed to join game"));

                requestQueue.addToRequestQueue(postReq);
            }
        };
    }

    public Observable<GameUpdateMessage> subscribe(String gameId) {
        final String dest = SUBSCRIBE_GAME_DEST + gameId;

        return new Observable<GameUpdateMessage>() {
            @Override
            protected void subscribeActual(Observer<? super GameUpdateMessage> observer) {
                client.topic(dest).subscribe(msg -> {
                    final GameUpdateMessage updateMessage = fromStompMsg(msg, GameUpdateMessage.class);
//                    Log.d("CLIENT", updateMessage.toString());
                    observer.onNext(updateMessage);
                }, observer::onError);
            }
        };
    }

    public void updatePlayer(String gameId, Vector direction) {
        final String dest = UPDATE_PLAYER_DEST + gameId;
        final String json = gson.toJson(
                new DirectionChangedMessage(config.getPlayerId(), direction), DirectionChangedMessage.class);

//        Log.d("CLIENT", json);
        client.send(dest, json).subscribe();
    }

    public void quit(String gameId) {
        final String dest = QUIT_GAME_DEST + gameId;
        final String json = gson.toJson(config.constructMessage(), PlayerJoinMessage.class);

//        Log.d("CLIENT", json);
        client.send(dest, json).subscribe();
    }

    private void openConnection() {
        client.connect();
    }

    public void closeConnection() {
        client.disconnect();
    }

    private <T> T fromStompMsg(StompMessage message, Class<? extends T> tClass) {
        return gson.fromJson(message.getPayload(), tClass);
    }

}

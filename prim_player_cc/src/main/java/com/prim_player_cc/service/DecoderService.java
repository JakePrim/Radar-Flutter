package com.prim_player_cc.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.prim_player_cc.decoder_cc.ProxyDecoderCC;
import com.prim_player_cc.source_cc.PlayerSource;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DecoderService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.prim_player_cc.service.action.FOO";
    private static final String ACTION_BAZ = "com.prim_player_cc.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.prim_player_cc.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.prim_player_cc.service.extra.PARAM2";

    private static ProxyDecoderCC proxyDecoderCC;

    public DecoderService() {
        super("DecoderService");
    }

    public static ProxyDecoderCC getProxyDecoderCC() {
        return proxyDecoderCC;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        proxyDecoderCC = new ProxyDecoderCC();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        proxyDecoderCC.destroy();
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionFoo(Context context, PlayerSource param1, String param2) {
        Intent intent = new Intent(context, DecoderService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DecoderService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {

    }
}

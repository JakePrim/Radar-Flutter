import 'dart:ui';

import 'package:flutter/material.dart';

/// App主题
class AppTheme {
  static double px1 =
      1 / PlatformDispatcher.instance.views.first.devicePixelRatio;

  static ThemeData getDarkThemeData() {
    Color primaryColor = Colors.deepPurple;

    return ThemeData(
      primaryColor: primaryColor,
      pageTransitionsTheme: const PageTransitionsTheme(builders: {
        TargetPlatform.android:
            ZoomPageTransitionsBuilder(allowEnterRouteSnapshotting: false)
      }),
      brightness: Brightness.dark,
      scaffoldBackgroundColor: const Color(0xff010201),
      appBarTheme: AppBarTheme(
          backgroundColor: primaryColor,
          titleTextStyle: const TextStyle(fontSize: 18, color: Colors.white)),
      listTileTheme: const ListTileThemeData(
          tileColor: Color(0xff181818), textColor: Color(0xffD6D6D6)),
      dividerTheme: DividerThemeData(
          color: const Color(0xff2f2f2f), space: px1, thickness: px1),
    );
  }

  static ThemeData getLightThemeData() {
    ColorScheme scheme = ColorScheme.fromSeed(seedColor: Colors.deepPurple);
    return ThemeData(
        scaffoldBackgroundColor: const Color(0xfff3f4f6),
        appBarTheme: AppBarTheme(
          backgroundColor: scheme.inversePrimary,
          titleTextStyle: const TextStyle(fontSize: 18, color: Colors.black),
        ),
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        listTileTheme: const ListTileThemeData(tileColor: Colors.white),
        pageTransitionsTheme: const PageTransitionsTheme(builders: {
          TargetPlatform.android: ZoomPageTransitionsBuilder(
            allowEnterRouteSnapshotting: false,
          )
        }),
        dividerTheme: DividerThemeData(
          color: const Color(0xffDee0e2),
          space: px1,
          thickness: px1,
        ));
  }
}

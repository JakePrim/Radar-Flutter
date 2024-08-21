import 'package:example/state_demo/02/app/theme.dart';
import 'package:example/state_demo/02/manager/app_theme_manager.dart';
import 'package:example/state_demo/02/manager/app_theme_scope.dart';
import 'package:example/state_demo/02/views/home_page.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const ManagerWrapper(child: MyApp()));
}

class ManagerWrapper extends StatelessWidget {
  final Widget child;

  const ManagerWrapper({super.key, required this.child});

  @override
  Widget build(BuildContext context) {
    return AppThemeScope(
      notifier: AppThemeManager(),
      child: child,
    );
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    //通过of方法获取可监听对象，建立依赖关系
    AppThemeManager appThemeManager = AppThemeScope.of(context);
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'istate',
      themeMode: appThemeManager.mode,
      darkTheme: AppTheme.getDarkThemeData(),
      theme: AppTheme.getLightThemeData(),
      home: const HomePage(title: '计数器'),
    );
  }
}

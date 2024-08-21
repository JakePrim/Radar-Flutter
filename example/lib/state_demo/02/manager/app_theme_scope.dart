import 'package:example/state_demo/02/manager/app_theme_manager.dart';
import 'package:flutter/material.dart';

class AppThemeScope extends InheritedNotifier<AppThemeManager> {
  AppThemeScope({super.key, required super.child, super.notifier});

  //定义一个of方法，可以根据上下文查询存储的可监听对象,建立依赖关系，当可监听对象发送通知时，对应的视图就会触发重新构建
  static AppThemeManager of(BuildContext context) {
    return context
        .dependOnInheritedWidgetOfExactType<AppThemeScope>()!
        .notifier!;
  }

  static AppThemeManager read(BuildContext context) {
    return context.getInheritedWidgetOfExactType<AppThemeScope>()!.notifier!;
  }
}

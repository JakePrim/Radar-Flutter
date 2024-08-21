import 'package:flutter/material.dart';

Map<ThemeMode, String> kThemeModeMap = const {
  ThemeMode.system: '跟随系统',
  ThemeMode.light: '浅色模式',
  ThemeMode.dark: '深色模式',
};

///使用全局变量对象，可以在代码的任何地方被访问
final AppThemeManager kAppThemeManager = AppThemeManager();

class AppThemeManager extends ChangeNotifier {
  ThemeMode _mode = ThemeMode.system;
  ThemeMode get mode => _mode;

  String get title => kThemeModeMap[mode]!;

  set mode(ThemeMode mode) {
    _mode = mode;
    notifyListeners(); //通知监听者
  }

  void switchTheme(bool dark) {
    if (dark) {
      mode = ThemeMode.dark;
    } else {
      mode = ThemeMode.light;
    }
  }
}

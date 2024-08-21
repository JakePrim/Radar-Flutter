import 'package:example/state_demo/02/manager/app_theme_manager.dart';
import 'package:example/state_demo/02/manager/app_theme_scope.dart';
import 'package:example/state_demo/02/views/settings/theme_model_setting.dart';
import 'package:flutter/material.dart';

class SettingsPage extends StatelessWidget {
  const SettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text('应用设置'),
      ),
      body: ListView(
        children: [
          Container(
            height: 15,
          ),
          const ThemeSettingItem()
        ],
      ),
    );
  }
}

class ThemeSettingItem extends StatelessWidget {
  const ThemeSettingItem({super.key});
  @override
  Widget build(BuildContext context) {
    AppThemeManager appThemeManager = AppThemeScope.of(context);
    Color primaryColor = Theme.of(context).primaryColor;
    const TextStyle subStyle = TextStyle(fontSize: 12, color: Colors.grey);
    const TextStyle titleStyle = TextStyle(fontSize: 16);
    return ListTile(
      leading: Icon(
        Icons.style,
        color: primaryColor,
      ),
      title: const Text(
        '深色模式',
        style: titleStyle,
      ),
      subtitle: Text(
        appThemeManager.title,
        style: subStyle,
      ),
      trailing: Icon(
        Icons.chevron_right,
        color: primaryColor,
      ),
      onTap: () => _toThemeModeSettingPage(context),
    );
  }

  void _toThemeModeSettingPage(BuildContext context) {
    Navigator.of(context)
        .push(MaterialPageRoute(builder: (_) => const ThemeModelSetting()));
  }
}

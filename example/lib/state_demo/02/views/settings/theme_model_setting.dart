import 'package:example/state_demo/02/components/toly_switch_list_tile.dart';
import 'package:example/state_demo/02/manager/app_theme_manager.dart';
import 'package:example/state_demo/02/manager/app_theme_scope.dart';
import 'package:flutter/material.dart';

class ThemeModelSetting extends StatefulWidget {
  const ThemeModelSetting({super.key});

  @override
  State<ThemeModelSetting> createState() => _ThemeModelSettingState();
}

class _ThemeModelSettingState extends State<ThemeModelSetting> {
  void _changeWithSystem(bool value, AppThemeManager appThemeManager) {
    ThemeMode newMode;
    if (value) {
      newMode = ThemeMode.system;
    } else {
      newMode = ThemeMode.light;
    }
    appThemeManager.mode = newMode; //设置mode 会触发notifylisteners 通知监听者渲染界面
  }

  @override
  Widget build(BuildContext context) {
    AppThemeManager appThemeManager = AppThemeScope.of(context);
    ThemeMode mode = appThemeManager.mode;
    const TextStyle title =
        TextStyle(fontSize: 16, fontWeight: FontWeight.bold);
    const TextStyle subtitle = TextStyle(fontSize: 12, color: Colors.grey);
    return Scaffold(
      appBar: AppBar(
        title: const Text('深色模式'),
      ),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            height: 15,
          ),
          TolySwitchListTile(
              title: const Text(
                '跟随系统',
                style: title,
              ),
              subtitle: const Text(
                '开启后,将跟随系统打开或关闭深色模式',
                style: subtitle,
              ),
              value: mode == ThemeMode.system,
              onChanged: (v) => _changeWithSystem(v, appThemeManager)),
          const Padding(
            padding: EdgeInsets.only(left: 10, top: 16, bottom: 6),
            child: Text('手动设置'),
          ),
          _buildItemTile(ThemeMode.light, mode, appThemeManager),
          const Divider(),
          _buildItemTile(ThemeMode.dark, mode, appThemeManager)
        ],
      ),
    );
  }

  Widget _buildItemTile(
      ThemeMode model, ThemeMode activeModel, AppThemeManager manager) {
    bool active = model == activeModel;
    Color iconColor = Theme.of(context).primaryColor;
    return ListTile(
      title: Text(kThemeModeMap[model]!),
      onTap: () => manager.mode = model,
      trailing: active ? Icon(Icons.check, size: 20, color: iconColor) : null,
    );
  }
}

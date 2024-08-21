import 'package:example/state_demo/02/manager/app_theme_manager.dart';
import 'package:example/state_demo/02/manager/app_theme_scope.dart';
import 'package:example/state_demo/02/views/setting_page.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  final String title;
  const HomePage({super.key, required this.title});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  int _counter = 0;

  void _incremenetCounter() {
    setState(() {
      _counter++;
    });
  }

  Widget _buildSwitch() {
    bool isDark = Theme.of(context).brightness == Brightness.dark;
    AppThemeManager appThemeManager = AppThemeScope.read(context);
    return Switch(
        value: isDark,
        inactiveTrackColor: Colors.white,
        trackOutlineColor: MaterialStateProperty.all(Colors.transparent),
        thumbIcon: MaterialStateProperty.all(isDark
            ? const Icon(Icons.dark_mode)
            : const Icon(Icons.light_mode)),
        onChanged: appThemeManager.switchTheme);
  }

  Widget _buildSettingButton() {
    return IconButton(
        onPressed: () {
          Navigator.of(context)
              .push(MaterialPageRoute(builder: (_) => const SettingsPage()));
        },
        icon: const Icon(Icons.settings));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        actions: [_buildSwitch(), _buildSettingButton()],
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text('下面是你点击按钮的次数:'),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incremenetCounter,
        tooltip: '增加',
        child: const Icon(Icons.add),
      ),
    );
  }
}

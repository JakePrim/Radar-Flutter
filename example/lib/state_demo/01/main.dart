import 'package:example/state_demo/01/disk_manager.dart';
import 'package:example/state_demo/01/notify_status.dart';
import 'package:example/state_demo/01/views/disk_view.dart';
import 'package:example/state_demo/01/views/message_panel.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'istate',
      theme: ThemeData(
        appBarTheme: const AppBarTheme(
            titleTextStyle: TextStyle(fontSize: 18, color: Colors.black)),
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const HomePage(
        title: '磁盘管理模拟',
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  final String title;
  const HomePage({super.key, required this.title});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  String useMsg = '磁盘使用记录:\n';
  String smsMsg = '消息通知记录:\n';

  late DiskManager _diskManager;

  final TextEditingController _useSize = TextEditingController(text: '100');

  @override
  void initState() {
    super.initState();
    _diskManager = DiskManager(total: 1024, callback: _handleDiskMessage);
  }

  void _handleDiskMessage(NotifyStatus status, String message) {
    switch (status) {
      case NotifyStatus.log:
        useMsg += "$message\n";
        break;
      case NotifyStatus.full:
      case NotifyStatus.threshold:
        smsMsg += "$message\n";
        break;
      default:
    }
  }

  ///开始用磁盘
  void _useDisk() {
    int? useSize = int.tryParse(_useSize.text);
    if (useSize != null) {
      setState(() {
        _diskManager.use(useSize);
      });
    }
  }

  ///格式化磁盘
  void _reset() {
    _diskManager.reset();
    setState(() {
      useMsg = '磁盘使用记录:\n';
      smsMsg = '消息通知记录:\n';
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        titleSpacing: 0,
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Row(
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 8.0),
              child: Text(widget.title),
            ),
            Expanded(
                child: CupertinoTextField(
              style: const TextStyle(fontSize: 14),
              controller: _useSize,
              placeholder: '磁盘使用量',
              placeholderStyle:
                  const TextStyle(fontSize: 14, color: Colors.grey),
            )),
          ],
        ),
        actions: [
          IconButton(
              onPressed: _useDisk, icon: const Icon(Icons.not_started_outlined))
        ],
      ),
      body: Column(
        children: [
          DiskView(
              total: _diskManager.total,
              usage: _diskManager.usage,
              id: '磁盘001',
              onReset: _reset),
          Expanded(child: MessagePanel(message: useMsg)),
          Expanded(child: MessagePanel(message: smsMsg)),
        ],
      ),
    );
  }
}

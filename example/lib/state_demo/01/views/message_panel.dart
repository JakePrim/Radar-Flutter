import 'package:flutter/material.dart';

class MessagePanel extends StatelessWidget {
  final String message;
  const MessagePanel({super.key, required this.message});

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.topLeft,
      padding: const EdgeInsets.all(0),
      margin: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
      decoration: BoxDecoration(
          color: Colors.grey.withOpacity(0.2),
          borderRadius: BorderRadius.circular(8)),
      child: SingleChildScrollView(
        child: Text(
          message,
          style: const TextStyle(fontSize: 12),
        ),
      ),
    );
  }
}

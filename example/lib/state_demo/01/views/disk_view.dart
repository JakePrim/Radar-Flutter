import 'package:flutter/material.dart';

class DiskView extends StatelessWidget {
  final int total;
  final int usage;
  final String id;
  final VoidCallback onReset;
  const DiskView(
      {super.key,
      required this.total,
      required this.usage,
      required this.id,
      required this.onReset});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 1,
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 12),
        child: Row(
          children: [
            const Padding(
              padding: EdgeInsets.only(right: 12.0),
              child: Icon(
                Icons.data_thresholding_outlined,
                size: 42,
              ),
            ),
            Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  id,
                  style: const TextStyle(
                      fontSize: 14, fontWeight: FontWeight.bold),
                ),
                Text(
                  '用量:$usage/$total',
                  style: const TextStyle(color: Colors.grey, fontSize: 12),
                ),
              ],
            ),
            const Spacer(),
            TextButton(onPressed: onReset, child: const Text('格式化')),
            const SizedBox(
              width: 8,
            ),
            Stack(
              alignment: Alignment.center,
              children: [
                CircularProgressIndicator(
                  value: usage / total,
                  backgroundColor: Colors.green,
                ),
                Text(
                  '${((usage / total) * 100).toStringAsFixed(1)}%',
                  style: const TextStyle(
                      fontSize: 8,
                      color: Colors.grey,
                      fontWeight: FontWeight.bold,
                      height: 1),
                )
              ],
            )
          ],
        ),
      ),
    );
  }
}

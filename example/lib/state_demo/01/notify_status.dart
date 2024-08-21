enum NotifyStatus {
  log, //记录磁盘的使用
  threshold, //磁盘达到阈值无法使用
  full, //磁盘已满
}

typedef DiskNotifyCallback = void Function(NotifyStatus status, String message);

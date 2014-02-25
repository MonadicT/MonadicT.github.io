---
layout: post
title: How to fix Wi-Fi connection issues in Ubuntu 13.04
tags: ubuntu, network, troubleshoot, tip
---

I have used Ubuntu for almost a decade now. I have always thought it
had a pretty good chance of being the alternative to MS Windows. After
the recent Windows 8 release, which definitely makes a Linux
alternative all the more imperative, I find it strangely ironic that
Ubuntu seems to be emulating just that. Sigh.

Rant over and now for a prductivity tip. When Ubuntu loses wifi
connectivity, restarting network-manager or networking will not
help. Reboot seems to be the only remedy after that.  Here are a
couple of bugs reported on this issue.

https://bugs.launchpad.net/ubuntu/+bug/1072518
https://bugs.launchpad.net/ubuntu/+source/dbus/+bug/1102507

There are many workarounds you can find on the net. However, on my
laptop, what seems to work always is the following.

```sh
sudo modprobe -r wl
sudo modprobe wl
```

Of course, the real solution may be to start using Arch Linux or
something much simpler than the Windows-wannabe Ubuntu :)

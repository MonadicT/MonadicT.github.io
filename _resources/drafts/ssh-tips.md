---
layout: post
title: SSH tips
tags: linux ssh tip
---

This is an introduction to `ssh` and its cousins `scp` and `sftp`. My
goal is to show how to use this near-universal facility conveniently.

`ssh` is a *Secure Socket Layer* application. You can think of `SSL`
as a telephone line immune to eavesdropping by others. Only you and
the person on the other end of the line can hear the conversation.

While SSL guarantees privacy between the communicating parties, there
is no promise of identity. The parties don't know who they are really
talking to. Identity must be guaranteed by a trusted
third-party. Without getting into the details, this is what a
*Certificate Authority* such as RSA and VeriSign guarantee. When you
view a website, such as https://google.com, in your browser, the
browser verifies the identity of the site you are connecting to and
indicates that the identity of the website has been verified. If the
verification didn't succeed, browsers alert the user of the problem.

When no third-party is available for identity management, the two
parties must establish trust out of band. Since SSL certificates
issued by CAs are expensive, most in-house usage of SSL involves
*self-signed* certificates. A *self-signed* certificate has
establishes an association between a name and a *cryptographic
credentials* also known as *keys*. *keys* have many magical properties
and one of them is that they can't be forged. But just like the key to
your house door, they should be safeguarded from thieves.

# Uses of ssh
The most common use of `ssh` is interacting with remote
servers. However, `ssh` is a highly flexible command and can do a lot
more than just allow you to connect to a remote server.

## Remote server connection

In this usage, you can think of *ssh* as a secure analogue of *sh* or
*bash*, the program which reads your commands, executes them and
displays the output on the terminal or screen. You connect to a remote
server using a command as shown below.

```
ssh userid@remote-server
```

When you execute this command, *ssh* authenticates you and then


When you execute `ssh server.foo`, *ssh* carries out these
actions. Be warned that you may see a slightly different behavior depending on how
things are configured.

- `ssh` client connects to the default SSL port (22) on `server.foo`
- The remote `ssh` server accepts the connection and conveys its identity and
other information to the client
- If the client is connecting to the server for the very first time,
and the certificate is self-signed, it displays the unique fingerprint
of the server's keys and asks if the connection should be made. If you
choose to continue, the server's identity is added to *known_hosts*
file. This is where you are expected to verify the fingerprint
detected by *ssh* client with what you have established out of
band.

When you are in a secure environment (is it possible?), you can
probably accept any key presented by the server.

You may see the scary "Man in the middle attack" message from
*ssh*. If a server whose identity has been stored in *known_hosts*
starts using a new key, *ssh* will detect it and flag it as a
warning. The remedy is to ensure that the server is indeed using a new
key and remove the old identity from *known_hosts*.

That is a superficial explanation of identity management of *ssh*
servers.

### SSH config file To use `ssh` and its
cousins, the command line goes something like this.

'''sh
ssh [options] user@host [command]
'''

/ssh/ has a very convenient facility to eliminate much typing, In =~/.ssh/config= file, you can define aliases. Here
is an example entry in that file.

'''sh
Host apollo 192.168.1.66
	User alice
'''

Now I can simply type, =ssh apollo= and it is equivalent to typing "ssh alice@apollo". The value of this
in being able to provide additional options, logging as different users and being able to forget actual IP addresses
or host names. This also works with =scp= and =sftp= of course.

### Password-less Remote shell access with SSH
First we generate a RSA key pair on the local system.

'''sh
ssh-keygen -t rsa
'''

It's highly recommended to protect your keys with a passphrase. =ssh-keygen= saves the keys as =id_rsa= and and =id_rsa.pub=.
Next we copy the public key =id_rsa.pub= to the remote system(s) using scp.

'''sh
scp ~/.ssh/id_rsa.pub user@remote-ip:
'''

Then we login to the remote system and append =id_rsa.pub= to =~/.ssh/authorized_keys2=.

'''sh
cat ~/id_rsa.pub >> ~/.ssh/authorized_keys2
chmod 700 ~/.ssh/authorized_keys2
'''

It's very important to set file mode to =700=. Most =sshd= will refuse to trust =authorized_keys2= if that file is accessible by others.
Now log out of the remote and try =ssh user@remote-ip=. You should be prompted for the passphrase after which you should be logged into the
remote system with no password!

Now it may not work as well as blogged here. Fortunately, =ssh= has =-v= switch to help. You can see the inner workings of ssh client and
figure out what is wrong. Note that repeating this option, =-vv= will show even more information.

The most common reason this doesn't work is the file permission on =~/.ssh/authorized_keys2= and naming the file =~/.ssh/authorized_keys=
or =~/.ssh/authorized_keys2=.

### Using ssh from scripts
It's is always a good idea to protect keys with pass phrases. However, this poses difficulties when you need to invoke =ssh= in scripts. =ssh-agent=
is the solution to being able to use keys protected with pass phrase.

You run 'ssh-agent' and 'ssh-add'. The latter prompts for the pass phrase of your protected key. If you attempt to run any command which needs the key,
the command can check =ssh-agent= and obtain the key. So, your pass phrase is safe and keys are accessible from scripts or programs.

`ssh-agent` can be accessed by remote `ssh` connections (unless disabled explicitly). What this means is that you can maintain your private key on
a single machine and still use that identity everywhere on the network. Private keys never leave the system on which they reside. `ssh-agent` performs
operations which require the private key and sends the result to the remote connection.

These programs have many other options to address other needs. /man/ pages are your friend.

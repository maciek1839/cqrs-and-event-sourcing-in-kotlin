package com.showmeyourcode.cqrseventsourcing.demo.domain;

public interface CommandHandler<R, C extends  Command<R>> {
    R handle(C command);
}

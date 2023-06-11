package com.showmeyourcode.cqrseventsourcing.demo.domain;

public interface CommandHandler<R, C extends  Command<R>> {
    void handle(C command);
}

package com.showmeyourcode.cqrseventsourcing.demo.domain;

public interface QueryHandler<R, C extends Query<R>> {
    R handle(C query);
}

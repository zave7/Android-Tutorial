package com.example.tutorial

interface OnDeleteListener {
    fun deleteAndAllReload(memo : MemoEntity)
    fun delete(memo : MemoEntity)
}
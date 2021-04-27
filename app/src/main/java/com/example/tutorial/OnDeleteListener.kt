package com.example.tutorial

interface OnDeleteListener {
    fun deleteAndAllReload(memo : MemoEntity)
    fun delete(memo : MemoEntity)

    /*
     * @return 상태 상테인 메모 리스트를 반환
     */
    fun deleteByStatus(memo : MemoEntity)
}
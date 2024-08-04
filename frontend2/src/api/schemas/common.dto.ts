export namespace CommonDto {
  export type Page<T> = {
    page: number
    pageSize: number
    totalElements: number
    items: T[]
  }
}
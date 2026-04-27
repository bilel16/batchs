export interface ResponseHABIL<T> {
  code: number;
  message: string;
  data: T;
}
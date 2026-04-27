export interface ResponseSAGA<T> {
  code: number;
  message: string;
  data: T;
}
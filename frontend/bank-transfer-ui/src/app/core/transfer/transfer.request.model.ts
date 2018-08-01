export interface TransferRequest {
  title?: string;
  value?: number;
  accountId?: {
    id?: number;
  };
  accountNo?: string;
  currency?: string;
  transferType?: string;
}

import pick from 'lodash/pick';
import { IShipmentInformationSearchDTO } from 'app/shared/model/shipment-info.model';

/**
 * Removes fields with an 'id' field that equals ''.
 * This function was created to prevent entities to be sent to
 * the server with relationship fields with empty an empty id and thus
 * resulting in a 500.
 *
 * @param entity Object to clean.
 */
export const cleanEntity = entity => {
  const keysToKeep = Object.keys(entity).filter(k => !(entity[k] instanceof Object) || (entity[k]['id'] !== '' && entity[k]['id'] !== -1));

  return pick(entity, keysToKeep);
};

/**
 * Simply map a list of element to a list a object with the element as id.
 *
 * @param idList Elements to map.
 * @returns The list of objects with mapped ids.
 */
export const mapIdList = (idList: ReadonlyArray<any>) =>
  idList.filter((entityId: any) => entityId !== '').map((entityId: any) => ({ id: entityId }));

import { AxiosPromise } from 'axios';
export interface IPayload<T> {
  type: string;
  payload: AxiosPromise<T>;
  meta?: any;
}

export declare type ICrudGetAllActionByVendor<T> = (
  vendorId?: number,
  page?: number,
  size?: number,
  sort?: string
) => IPayload<T> | ((dispatch: any) => IPayload<T>);

export declare type ICrudGetAllActionByDTO<T> = (
  shipmentSearch?: IShipmentInformationSearchDTO,
  page?: number,
  size?: number,
  sort?: string
) => IPayload<T> | ((dispatch: any) => IPayload<T>);

export declare type IPayloadResult<T> = ((dispatch: any) => IPayload<T> | Promise<IPayload<T>>);

export declare type ICrudGetActionAsync<T> = (id: string | number) => IPayload<T> | ((dispatch: any) => IPayload<T>) | IPayloadResult<T>;

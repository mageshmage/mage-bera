import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import {
  cleanEntity,
  ICrudGetAllActionByDTO,
  ICrudGetActionAsync,
  ICrudSearchAction as ICrudSearchActionVendor
} from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentInfo, defaultValue } from 'app/shared/model/shipment-info.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTINFOS: 'shipmentInfomation/SEARCH_SHIPMENTINFOS',
  FETCH_SHIPMENTINFO_LIST: 'shipmentInfomation/FETCH_SHIPMENTINFO_LIST',
  FETCH_SHIPMENTINFO: 'shipmentInfomation/FETCH_SHIPMENTINFO',
  CREATE_SHIPMENTINFO: 'shipmentInfomation/CREATE_SHIPMENTINFO',
  UPDATE_SHIPMENTINFO: 'shipmentInfomation/UPDATE_SHIPMENTINFO',
  DELETE_SHIPMENTINFO: 'shipmentInfomation/DELETE_SHIPMENTINFO',
  SEARCH_CONSIGNMENTNO: 'shipmentInfomation/SEARCH_CONSIGNMENTNO',
  RESET: 'shipmentInfomation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShipmentInfo>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ShipmentInformationState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentInformationState = initialState, action): ShipmentInformationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTINFOS):
    case REQUEST(ACTION_TYPES.SEARCH_CONSIGNMENTNO):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHIPMENTINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SHIPMENTINFO):
    case REQUEST(ACTION_TYPES.UPDATE_SHIPMENTINFO):
    case REQUEST(ACTION_TYPES.DELETE_SHIPMENTINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_SHIPMENTINFOS):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHIPMENTINFO):
    case FAILURE(ACTION_TYPES.CREATE_SHIPMENTINFO):
    case FAILURE(ACTION_TYPES.UPDATE_SHIPMENTINFO):
    case FAILURE(ACTION_TYPES.DELETE_SHIPMENTINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case FAILURE(ACTION_TYPES.SEARCH_CONSIGNMENTNO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
        entity: {}
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTINFOS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTINFO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.SEARCH_CONSIGNMENTNO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHIPMENTINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_SHIPMENTINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHIPMENTINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shipment-informations';
const apiSTOSearchUrl = 'api/shipment-informationsSearch';
const apiSearchConsignmentUrl = 'api/shipment-trackingssearchpublic';
const apiSearchUrl = 'api/_search/shipment-infos';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentInfo> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTINFOS,
  payload: axios.get<IShipmentInfo>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getSearchWithConsignmentNo: ICrudSearchActionVendor<IShipmentInfo> = (query, vendorId, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_CONSIGNMENTNO,
  payload: axios.get<IShipmentInfo>(`${apiSearchConsignmentUrl}?query=${query}&vendorId=${vendorId}`)
});

export const getEntities: ICrudGetAllAction<IShipmentInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTINFO_LIST,
    payload: axios.get<IShipmentInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntitiesSearch: ICrudGetAllActionByDTO<IShipmentInfo> = (searchData, page, size, sort) => {
  const requestUrl = `${apiSTOSearchUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTINFO_LIST,
    payload: axios.post<IShipmentInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`, searchData)
  };
};

export const getEntity: ICrudGetActionAsync<IShipmentInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.FETCH_SHIPMENTINFO,
    payload: axios.get<IShipmentInfo>(requestUrl)
  });
  return result;
};

export const createEntity: ICrudPutAction<IShipmentInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHIPMENTINFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShipmentInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHIPMENTINFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShipmentInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHIPMENTINFO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

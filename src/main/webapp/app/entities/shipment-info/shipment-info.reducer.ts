import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShipmentInfo, defaultValue } from 'app/shared/model/shipment-info.model';

export const ACTION_TYPES = {
  SEARCH_SHIPMENTINFOS: 'shipmentInfo/SEARCH_SHIPMENTINFOS',
  FETCH_SHIPMENTINFO_LIST: 'shipmentInfo/FETCH_SHIPMENTINFO_LIST',
  FETCH_SHIPMENTINFO: 'shipmentInfo/FETCH_SHIPMENTINFO',
  CREATE_SHIPMENTINFO: 'shipmentInfo/CREATE_SHIPMENTINFO',
  UPDATE_SHIPMENTINFO: 'shipmentInfo/UPDATE_SHIPMENTINFO',
  DELETE_SHIPMENTINFO: 'shipmentInfo/DELETE_SHIPMENTINFO',
  RESET: 'shipmentInfo/RESET'
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

export type ShipmentInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: ShipmentInfoState = initialState, action): ShipmentInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SHIPMENTINFOS):
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
    case SUCCESS(ACTION_TYPES.SEARCH_SHIPMENTINFOS):
    case SUCCESS(ACTION_TYPES.FETCH_SHIPMENTINFO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
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

const apiUrl = 'api/shipment-infos';
const apiSearchUrl = 'api/_search/shipment-infos';

// Actions

export const getSearchEntities: ICrudSearchAction<IShipmentInfo> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SHIPMENTINFOS,
  payload: axios.get<IShipmentInfo>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IShipmentInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTINFO_LIST,
    payload: axios.get<IShipmentInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IShipmentInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHIPMENTINFO,
    payload: axios.get<IShipmentInfo>(requestUrl)
  };
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

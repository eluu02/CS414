import Ajv from 'ajv';
import * as configSchema from '../../schemas/ConfigResponse';
import * as testSchema from '../../schemas/TestResponse';
import * as userSchema from '../../schemas/UserResponse';
import * as loginSchema from '../../schemas/LoginResponse';
import * as createuserSchema from '../../schemas/CreateUserResponse';
import * as createinvSchema from '../../schemas/CreateInvResponse';
import * as acceptinvSchema from '../../schemas/AcceptInvResponse';
import * as declineinvSchema from '../../schemas/DeclineInvResponse';
import * as invhistSchema from '../../schemas/InviteHistResponse';
import * as matchhistSchema from '../../schemas/MatchHistResponse';
import * as openmatchSchema from '../../schemas/OpenMatchResponse';
import * as movepieceSchema from '../../schemas/MoveResponse';




import { LOG } from './constants';

const SCHEMAS = {
    config: configSchema,
    test: testSchema,
    user: userSchema,
    login: loginSchema,
    createuser: createuserSchema,
    createinv: createinvSchema,
    acceptinv: acceptinvSchema,
    declineinv: declineinvSchema,
    invhist: invhistSchema,
    matchhist: matchhistSchema,
    openmatch: openmatchSchema,
    movepiece: movepieceSchema

}

export async function sendAPIRequest(requestBody, serverUrl) {
    const response = await sendRequest(requestBody, serverUrl);

    if (response!==null && isRequestNotSupported(requestBody)) {
        throw new Error(`sendAPIRequest() does not have support for type: ${requestBody.requestType}. Please add the schema to 'SCHEMAS'.`);
    }
    if (response!==null && isJsonResponseValid(response, SCHEMAS[requestBody.requestType])) {
        return response;
    }
    LOG.error(`Server ${requestBody.requestType} response json is invalid. Check the Server.`);
    return null;
}

export function isRequestNotSupported(requestBody){
    return (!Object.keys(SCHEMAS).includes(requestBody.requestType));
}

async function sendRequest(requestBody, serverUrl) {
    const fetchOptions = {
        method: "POST",
        body: JSON.stringify(requestBody)
    };

    try {
        const response = await fetch(`${serverUrl}/api/${requestBody.requestType}`, fetchOptions);
        if (response.ok) {
            return response.json();
        } else {
            LOG.error(`Request to server ${serverUrl} failed: ${response.status}: ${response.statusText}`);
        }

    } catch (err) {
        LOG.error(`Request to server failed : ${err}`);
    }

    return null;
}

export function getOriginalServerUrl() {
    const serverProtocol = location.protocol;
    const serverHost = location.hostname;
    const serverPort = location.port;
    const alternatePort = process.env.SERVER_PORT;
    return `${serverProtocol}\/\/${serverHost}:${(!alternatePort ? serverPort : alternatePort)}`;
}

export function isJsonResponseValid(object, schema) {
    if (object && schema) {
        const anotherJsonValidator = new Ajv();
        const validate = anotherJsonValidator.compile(schema);
        return validate(object);
    }
    LOG.error(`bad arguments - isJsonResponseValid(object: ${object}, schema: ${schema})`);
    return false;
}


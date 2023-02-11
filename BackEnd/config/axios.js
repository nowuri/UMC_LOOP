const { config } = require('process');
const { response, errResponse } = require('./response.js');
const baseResponseStatus = require('./baseResponseStatus.js');
const axios = require('axios').default;
const xml2js = require('xml2js');

exports.myParser = async (xml) => {
    const promise = await new Promise((resolve, reject) => {
        const parser = new xml2js.Parser();
      
        parser.parseString(xml, (error, result) => {
            if (error) reject(error);
            else resolve(result);
          });
        });
    return promise;
}

exports.xmlToJson = async(config,res) =>{

    const myParser = async (xml) => {
        const promise = await new Promise((resolve, reject) => {
            const parser = new xml2js.Parser();
          
            parser.parseString(xml, (error, result) => {
                if (error) reject(error);
                else resolve(result);
              });
            });
        return promise;
    }


    try {
        const policyResponse = await axios(config);
        console.log(policyResponse.data);
        const parsedData = await myParser(policyResponse.data);
      
        console.dir(parsedData, { depth: null });
        return res.send(response(baseResponseStatus.SUCCESS, parsedData));
      
    } catch (error) {
        console.error(error);
        return res.send(errResponse(baseResponseStatus.SERVER_ERROR));
    }
}


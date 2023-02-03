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
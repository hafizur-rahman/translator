const http = axios.create({
    baseURL: '/api',
    headers: { 'Content-Type': 'application/json' },
});

var vocabulary = Vue.extend({
    template: `<div id="vocabulary" class="container-fluid">
        <form class="form-inline container-fluid myForm">
            <form class="form-horizontal container-fluid">
                <div class="form-group">                
                    <label for="fname">URL:</label>
                    <input type="text" class="container-fluid" name="url" size="100" id="url" v-model="url">
                    <input class="btn btn-primary" type="submit" value="Parse Site" @click.prevent="parseSite">
                </div>
            </form>
        </form>

        <form class="form-horizontal container-fluid">
            <div class="form-group">
                <div class="col-sm-10">
                    <textarea placeholder="Input Text" rows="3" class="form-control" id="InputTextarea" v-model="message"></textarea>
                </div>
            </div>
            <div class="text-center myForm"> 
                <input class="btn btn-primary" type="submit" value="Parse Text" @click.prevent="parseText">
            </div>
        </form>
        <table v-if="results" class="container-fluid table table-bordered">
            <thead>
                <tr>
                    <th v-for="title in titles">
                        {{ title }}
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="word in results.results">
                    <td v-for="col in columns">
                        {{word[col]}}
                    </td>
                </tr>
            </tbody>
        </table>
        </div>`,
    data: function () {
        return {
            url: "",
            message: "",
            results: "",
            titles: ["Word", "Reading", "Pronunciation", "English Meaning"],
            columns: ["baseForm", "reading", "pronunciation", "meaning"]
        }
    },
    methods: {
        parseSite: function () {
            http.post("/parse-url", { "url": this.url })
                .then(response => {
                    this.message = response.data
                })
                .catch(error => {
                    console.log(error);
                });
        },
        parseText: function () {
            http.post("/parse-text", { "text": this.message })
                .then(response => {
                    this.results = response.data
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }
})


new Vue({
    el: '#app',
    components: {
        'vocabulary': vocabulary,
    },
    data: {
        ok: false
    },
    created: function () {
        if (VOCABULARY_SERVICE_URI) {
            this.ok = true;
        } else {
            alert('Vocabulary service not found.');
        }
    }
})
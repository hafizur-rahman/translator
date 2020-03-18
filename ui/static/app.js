const http = axios.create({
    baseURL: '/api',
    headers: { 'Content-Type': 'application/json' },
});

var vocabulary = Vue.extend({
    template: `<div id="vocabulary" class="container-fluid">
        <div>Input Text:</div>
        <form class="form-horizontal container-fluid">
            <div class="form-group">
                <div class="col-sm-10">
                    <textarea placeholder="Input Text" rows="3" class="form-control" id="InputTextarea" v-model="message"></textarea>
                </div>
            </div>
            <div class="text-center myForm"> 
                <input class="btn btn-primary" type="submit" value="Send" @click.prevent="submit">
            </div>
        </form>
        <table v-if="results" class="container-fluid table table-bordered">
            <thead>
                <tr>
                    <th v-for="col in columns">
                        {{ col }}
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
            message: "",
            results: null,
            columns: ["baseForm","reading",	"pronunciation","meaning"]
        }
    },
    methods: {
        submit: function () {
            http.post("/", { "text": this.message })
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